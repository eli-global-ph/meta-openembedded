SUMMARY = "Python-apt is a wrapper to use features of apt from python."
LICENSE = "GPL-2.0-only & FSFAP"
LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=0636e73ff0215e8d672dc4c32c317bb3 \
                    file://debian/copyright;md5=4ed7b6862ca422678b17e7d4ed592285"

SRC_URI = "git://salsa.debian.org/apt-team/python-apt.git;protocol=https;branch=main"

SRCREV = "6145b6484d220685edfd922d364afbf065127efe"

S = "${WORKDIR}/git"

inherit setuptools3
PIP_INSTALL_PACKAGE = "python_apt"
PYPA_WHEEL = "${PIP_INSTALL_DIST_PATH}/${PIP_INSTALL_PACKAGE}-0.0.0-*.whl"

DEPENDS += "apt"
RDEPENDS:${PN} += "apt python3-core"

FILES:${PN} = "${libdir} ${datadir}/python-apt"
