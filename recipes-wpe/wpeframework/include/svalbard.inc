# Provides configuration for the Svalbard Plugin
WPE_SVALBARD_AUTOSTART ??= "true"
WPE_SVALBARD_OUTOFPROCESS ??= "false"
WPE_SVALBARD_USER ??= ""
WPE_SVALBARD_GROUP ??= ""

PACKAGECONFIG[svalbard] = "\
    -DPLUGIN_SVALBARD=ON \
    -DPLUGIN_SVALBARD_AUTOSTART="${WPE_SVALBARD_AUTOSTART}" \
    -DPLUGIN_SVALBARD_OUTOFPROCESS="${WPE_SVALBARD_OUTOFPROCESS}" \
    -DPLUGIN_SVALBARD_USER="${WPE_SVALBARD_USER}" \
    -DPLUGIN_SVALBARD_GROUP="${WPE_SVALBARD_GROUP}" \
    ,-DPLUGIN_SVALBARD=OFF, \
"

