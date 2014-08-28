#include "SteamUtils.h"

void SteamUtils::log(const char* format, ...) {
    va_list args;
    va_start(args, format);

    vprintf(format, args);
    fflush(stdout);

    va_end(args);
}
