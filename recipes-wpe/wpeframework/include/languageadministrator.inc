# Provides configuration for the LanguageAdministrator Plugin
WPE_LANGUAGEADMINISTRATOR_AUTOSTART ??= "true"

PACKAGECONFIG[languageadministrator] = "\
    -DPLUGIN_LANGUAGEADMINISTRATOR=ON \
    -DPLUGIN_LANGUAGEADMINISTRATOR_AUTOSTART="${WPE_LANGUAGEADMINISTRATOR_AUTOSTART}" \
    ,-DPLUGIN_LANGUAGEADMINISTRATOR=OFF, \
"

