/****************************************************************************
 * Module    : platform.c
 * Function  : Print a string identifying the current O/S platform.
 ****************************************************************************/
#define	_CRT_SECURE_NO_WARNINGS 1		/* Inhibit W32 sscanf warnings		*/
#include <stdio.h>
#include <string.h>
#ifndef WIN32
#include <sys/utsname.h>
#include <unistd.h>
#endif
#include <stdlib.h>
#include <errno.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>

static char platform_uname[2048]; /* The text returned from 'uname -a' */
static int flag_64bit; /* Set if to use 64 bit compilation mode */

static void FindPlatform(char*);
static char* findPortkey(char* platform);
#ifndef WIN32
static int run_command(const char *cmd, char *output_str, size_t len);
#endif

#ifndef __cplusplus
#undef MAX
#define MAX(a,b) ((a) > (b) ? (a) : (b))
#undef MIN
#define MIN(a,b) ((a) < (b) ? (a) : (b))
#endif

typedef struct platform_map {
  char* platform;
  char* portkey;
} platform_map_t;

platform_map_t platform_map[] = {
    { "WIN32_VC80",       "WIN32_VC80", },
    { "WIN32_VC90",       "WIN32_VC90", },
    { "WIN32_VC100",      "WIN32_VC100", },
    { "WIN64_VC100",      "WIN64_VC100", },
    { "linux_as5_x86_64", "LINUX_ELF_AS5", },
    { "linux_as6_x86_64", "LINUX_ELF_AS6", },
    { "hp-ux_ia64_64",    "HPUX_IA64", },
    { "aix_5_3",          "AIX_5", },
    { "aix_6_1",          "AIX_6", },
    { "aix_7_1",          "AIX_7", },
    { "sunos_64",         "SUN", },
    { "sunos_x86_64",     "SUN_X86", },
    { "sunos_11_64",      "SUN_11", },
    { "sunos_11_x86_64",  "SUN_11_X86", },
    0,
};
/*********************************************************************************
 *
 * Function:             FindPlatform()
 *
 * Description:  Find out what platform we are building on.
 *
 * Parameters:   None.
 *
 * Return Value: None (we update the 'platform' static variable [P.K. which gets
 *               overwritten somewhere, see TR100599], AND platform_uname).
 *
 *********************************************************************************/
static void FindPlatform(char *platform) {
#ifndef WIN32
  struct utsname uts;
  char * p1;
  char * p2;
  char * p3;
  char * p4;
  char * p5;
  int rc;
#endif
  strcpy(platform_uname, "");

  /*
   * Try using the 'uname' command to determine platform type.
   * We will use the data later.
   */
#ifdef WIN32
#if defined(_WIN64)
#if defined(_MSC_VER) && (_MSC_VER >= 1600)
  strcpy(platform , "WIN64_VC100");
#elif defined(_MSC_VER) && (_MSC_VER >= 1500)
  strcpy(platform , "WIN64_VC90");
#else
  strcpy(platform , "WIN64");
#endif
#elif defined(_MSC_VER) && (_MSC_VER >= 1600)
  strcpy(platform , "WIN32_VC100");
#elif defined(_MSC_VER) && (_MSC_VER >= 1500)
  strcpy(platform , "WIN32_VC90");
#elif defined(_MSC_VER) && (_MSC_VER >= 1400)
  strcpy(platform , "WIN32_VC80");
#else
  strcpy(platform , "WIN32");
#endif
#elif __MVS__
  strcpy(platform,"OS390");
#else

  /* obtain machine & os information */
  rc = uname(&uts);
  if (rc == -1) {
    /* uname failed */
    fprintf(stderr, "Error, uname call failed - errno %d, '%s'\n", errno,
        strerror(errno));
    return;
  }
  sprintf(platform_uname, "%s %s %s %s %s", uts.sysname, uts.nodename,
      uts.release, uts.version, uts.machine);
  strcpy(platform, uts.sysname);

  /*
   * System/platform type
   */
  p1 = uts.sysname;
  /*
   * node name name
   */
  p2 = uts.nodename;
  /*
   * Minor OS release version
   */
  p3 = uts.release;
  /*
   * Major OS release version
   */
  p4 = uts.version;
  /*
   * machine type
   */
  p5 = uts.machine;

  /*
   * We need the version info for some systems
   */
  if (strcmp("AIX", p1) == 0) /* AIX */
  {
    sprintf(platform, "%s_%s_%s", p1, p4, p3);
  } else if (strcmp("HP-UX", p1) == 0) /* HP-UX */
  {
    /* For HP-UX, force 64-bit always */
    flag_64bit++;

    if (strcmp("ia64", p5) == 0) {
      sprintf(platform, "%s_%s", p1, p5);
    } else {
      sprintf(platform, "%s", p1);
    }
  } else if (strcmp("Linux", p1) == 0) /* Woo hoo, it's GNU/Linux */
  {
    int rc1 = 0, rc2 = 0;
    int lsb_okay = 0;
    char cmd[512];
    char relver_str[512];
    char desc_str[512];

    memset(relver_str, '\0', sizeof(relver_str));
    memset(desc_str, '\0', sizeof(desc_str));

    snprintf(cmd, sizeof(cmd), "lsb_release -r 2>/dev/null");
    rc1 = run_command(cmd, relver_str, sizeof(relver_str));
    snprintf(cmd, sizeof(cmd), "lsb_release -d 2>/dev/null");
    rc2 = run_command(cmd, desc_str, sizeof(desc_str));

    if (rc1 == 0 && rc2 == 0) {
      lsb_okay = 1;
    }

    if (strcmp(p5, "x86_64") == 0) {
      if (lsb_okay) {
        if (relver_str[0] == '5') {
          sprintf(platform, "%s_as5_%s", p1, p5);
        } else {
          /* We are assuming that if it is not Redhat Enterprise 5 then it must be newer */
          sprintf(platform, "%s_as6_%s", p1, p5);
        }
      } else {
        sprintf(platform, "%s_as5_%s", p1, p5);
      }
    } else if (strcmp(p5, "i686") == 0) {
      /*
       * if i686, add this to platform definition, then we can use Intel optimisation flags
       * to improve performance
       */
      sprintf(platform, "%s_%s", p1, p5);
    } else if (strcmp(p5, "s390x") == 0) {
      sprintf(platform, "%s_%s", p1, p5);
    } else if (strcmp(p5, "ppc64") == 0) {
      sprintf(platform, "%s_ppc_64", p1);
    } else if (strcmp(p5, "ppc") == 0) {
      sprintf(platform, "%s_ppc", p1);
    } else {
      sprintf(platform, "%s", p1);
    }
  } else {
    sprintf(platform, "%s", p1);
  }

  /* Solaris test */
  if (strcmp("SunOS", p1) == 0) {
    if (strcmp("5.9", p3) == 0 || strcmp("5.10", p3) == 0 || strcmp("5.11", p3)
        == 0) {
      /* Solaris 9 or Solaris 10 found: set the 64-bit flag */
      flag_64bit++;
    }
    if (strcmp("5.11", p3) == 0) {
      /* Solaris 11 found: adjust the platform */
      strcat(platform, "_11");
    }
    /*
     * if Solaris x86 then set platform id
     */
    if (strcmp(p5, "i86pc") == 0) {
      strcat(platform, "_x86");
    }
  }

  p1 = platform;
  while (*p1 != '\0') {
    *p1 = tolower(*p1);
    p1++;
  }
#endif

  /*
   * If running in 64 bit mode, add this to the platform definition.
   */
  if (flag_64bit) {
    strcat(platform, "_64");
  }
}

#ifndef WIN32
int run_command(const char *command, char *output_str, size_t len) {
  FILE * fd;
  char buffer[2048];
  char *p1, *p2;
  int rc = 0;

  if ((fd = popen(command, "r")) != NULL) {
    if (fgets(buffer, sizeof(buffer) - 1, fd) != NULL) {
      p1 = strtok(buffer, ":");
      p2 = strtok(NULL, ":");

      if (p2 != NULL) {
        size_t minlen;

        /* trim off the leading TAB or spaces */
        while (*p2 == ' ' || *p2 == 0x09)
          ++p2;

        minlen = MIN( strlen( p2 ) - 1, len );
        strncpy(output_str, p2, minlen);
      }
    } else {
      rc = 1;
    }
  }
  pclose(fd);
  return rc;
}
#endif

static char* findPortkey(char* platform) {
  char* portkey = NULL;
  platform_map_t* tmp;

  if (platform) {
    for (tmp = platform_map; tmp && tmp->platform; tmp++) {
      if (!strcmp(tmp->platform, platform)) {
        portkey = tmp->portkey;
        break;
      }
    }
  }

  if (!portkey)
    portkey = "UNKNOW_PORTKEY";

  return portkey;
}

int main(int argc, char * argv[]) {
  char platform[256]; /* The name of the platform we are on */
  char* portkey = NULL;
  FindPlatform(platform);
  portkey = findPortkey(platform);
  printf("%s", portkey);

  return 0;
}
