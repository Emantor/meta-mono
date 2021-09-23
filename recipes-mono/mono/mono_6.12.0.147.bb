require mono-6.xx.inc
require mono-mit-bsd-6xx.inc
require ${PN}-base.inc
require mono-${PV}.inc

RDEPENDS:${PN}-dev =+ " zlib "

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${BASEPV}.tar.xz \
           file://shm_open-test-crosscompile.diff \
           file://0004-Disable-DebuggerTests.Crash-since-it-fails-on-Linux-.patch \
           file://0007-Don-t-include-mono-dtrace.h-when-generating-offsets.patch \
           file://0008-2020-02-marshal-Fix-VARIANT-and-BSTR-marshaling-in-s.patch \
           file://0010-Fix-the-System.String.Replace-throwing-NotImplemente.patch \
           file://0014-2020-02-Backport-r4-conv-i-fixes-20986.patch \
           file://0016-arm64-Fix-wrong-marshalling-in-gsharedvt-transition-.patch \
           file://0019-MonoIO-Wrap-calls-to-open-in-EINTR-handling-21042.patch \
           file://0020-2020-02-Fix-leak-in-assembly-specific-dllmap-lookups.patch \
           file://0024-2020-02-Start-a-dedicated-thread-for-MERP-crash-repo.patch \
           file://0025-2020-02-Fix-memory-leak-during-data-registration-211.patch \
"


addtask fixup_config after do_patch before do_configure

do_fixup_config() {
        sed 's|$mono_libdir/libMonoPosixHelper@libsuffix@|libMonoPosixHelper.so|g' -i ${S}/data/config.in
        sed 's|@X11@|libX11.so.6|g' -i ${S}/data/config.in
        sed 's|@libgdiplus_install_loc@|libgdiplus.so.0|g' -i ${S}/data/config.in
}

PACKAGES += "${PN}-profiler "
FILES:${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"

INSANE_SKIP:${PN}-libs += "dev-so"
