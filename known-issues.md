---
layout: default
---

## Known issues

### Linux

#### unable to load libsdkencryptedappticket.so

As of *Steamworks SDK v1.42*, SteamEncryptedAppTicket.loadLibraries() fails because it is unable to load libsdkencryptedappticket.so, even if it's available and right next to libsteamworks4j-encryptedappticket.so in the library path.

I still don't understand why this happens, but I found a workaround which needs *patchelf* to modify the shared library's *soname*. This only needs to be done once after setting up or updating the ```/sdk``` folder.

Last time I tried, the version of *patchelf* distributed on Debian/Ubuntu/Mint was outdated and didn't provide the needed option yet. Here are the steps to build and install *patchelf* from [source code](https://github.com/NixOS/patchelf.git):

{% highlight %}
./bootstrap.sh
./configure
make
sudo make install
{% endhighlight %}

With *patchelf* installed, navigate to ```/sdk/public/steam/lib/linux64``` and run:

{% highlight %}
patchelf --set-soname libsdkencryptedappticket.so libsdkencryptedappticket.so
{% endhighlight %}

After that, you can copy this library to your resource path, or use Maven to package a version of *steamworks4j-server* with all libraries included:

{% highlight %}
mvn [package|install] -pl server -Plibs
{% endhighlight %}
