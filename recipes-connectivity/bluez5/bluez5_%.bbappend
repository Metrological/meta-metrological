SYSTEMD_SERVICE_${PN}_remove = "${@bb.utils.contains('MACHINE_FEATURES', "bluetooth", "bluetooth.service", "", d)}"
SYSTEMD_SERVICE_${PN}_remove = "${@bb.utils.contains('MACHINE_FEATURES', "bluetooth", "brcm43438.service", "", d)}"

do_install_append() {
    install -m 0644 ${S}/lib/mgmt.h ${D}${includedir}/bluetooth/
}

#Enable this for broadcom as well, during the removal of systemd services
do_install_append_rpi() {
    if [ -d ${D}${systemd_unitdir} ]
    then
        rm -rf ${D}${systemd_unitdir}
    fi
    if [ -f ${D}${INIT_D_DIR}/bluetooth ]
    then
        rm -rf ${D}${INIT_D_DIR}/bluetooth
    fi
}
