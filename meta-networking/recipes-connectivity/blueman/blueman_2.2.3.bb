DESCRIPTION = "Blueman is a GTK+ Bluetooth Manager"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "bluez5 python3-pygobject python3-cython-native python3-setuptools-native intltool-native"

inherit autotools gettext systemd gsettings pkgconfig python3native gtk-icon-cache

SRC_URI = " \
    https://github.com/blueman-project/blueman/releases/download/${PV}/blueman-${PV}.tar.xz \
    file://0001-Search-for-cython3.patch \
    file://0002-fix-fail-to-enable-bluetooth.patch \
"
SRC_URI[sha256sum] = "6edd791da6afd8f610ffb08d5138cfcf50e6257ad30efad686287f3a2be106e9"

EXTRA_OECONF = " \
    --disable-appindicator \
    --disable-runtime-deps-check \
    --disable-schemas-compile \
"

SYSTEMD_SERVICE:${PN} = "${BPN}-mechanism.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

RRECOMENDS_${PN} += "adwaita-icon-theme"
RDEPENDS:${PN} += " \
    python3-core \
    python3-dbus \
    packagegroup-tools-bluetooth \
"

PACKAGECONFIG ??= "thunar"
PACKAGECONFIG[thunar] = "--enable-thunar-sendto,--disable-thunar-sendto"

FILES:${PN} += " \
    ${datadir}/dbus-1 \
    ${datadir}/Thunar \
    ${systemd_user_unitdir} \
    ${exec_prefix}${systemd_system_unitdir} \
    ${PYTHON_SITEPACKAGES_DIR} \
"

FILES:${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/_blueman.a"

# In code, path to python is a variable that is replaced with path to native version of it
# during the configure stage, e.g ../recipe-sysroot-native/usr/bin/python3-native/python3.
# Replace it with #!/usr/bin/env python3
do_install:append() {
    sed -i "1s/.*/#!\/usr\/bin\/env python3/" ${D}${prefix}/libexec/blueman-rfcomm-watcher \
                                              ${D}${prefix}/libexec/blueman-mechanism \
                                              ${D}${bindir}/blueman-adapters \
                                              ${D}${bindir}/blueman-applet \
                                              ${D}${bindir}/blueman-manager \
                                              ${D}${bindir}/blueman-sendto \
                                              ${D}${bindir}/blueman-services \
                                              ${D}${bindir}/blueman-tray
}
