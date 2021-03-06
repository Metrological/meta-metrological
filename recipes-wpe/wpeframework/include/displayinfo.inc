# Provides the WPE DisplayInfo plugin settings

WPE_DISPLAYINFO_AUTOSTART ??= "true"
WPE_DISPLAYINFO_USE_BEST_MODE ??= "true"
WPE_DISPLAYINFO_EDID_FILEPATH ??= ""
WPE_DISPLAYINFO_DRM_DEVICE_NAME ??= "/dev/drm/card0"
WPE_DISPLAYINFO_HDCP_LEVEL_FILEPATH ??= ""
WPE_DISPLAYINFO_HDR_LEVEL_FILEPATH ??= ""
WPE_DISPLAYINFO_GPU_MEMORY_FILE ??= "/proc/meminfo"
WPE_DISPLAYINFO_GPU_MEMORY_FREE_PATTERN ??= "CmaFree:"
WPE_DISPLAYINFO_GPU_MEMORY_TOTAL_PATTERN ??= "CmaTotal:"
WPE_DISPLAYINFO_GPU_MEMORY_UNIT_MULTIPLIER ??= "1024"

PACKAGECONFIG[displayinfo] = "\
    -DPLUGIN_DISPLAYINFO=ON \
    -DPLUGIN_DISPLAYINFO_USE_BEST_MODE=${WPE_DISPLAYINFO_USE_BEST_MODE} \
    -DPLUGIN_DISPLAYINFO_DRM_DEVICE_NAME=${WPE_DISPLAYINFO_DRM_DEVICE_NAME} \
    -DPLUGIN_DISPLAYINFO_EDID_FILEPATH=${WPE_DISPLAYINFO_EDID_FILEPATH} \
    -DPLUGIN_DISPLAYINFO_HDCP_LEVEL_FILEPATH=${WPE_DISPLAYINFO_HDCP_LEVEL_FILEPATH} \
    -DPLUGIN_DISPLAYINFO_HDR_LEVEL_FILEPATH=${WPE_DISPLAYINFO_HDR_LEVEL_FILEPATH} \
    -DPLUGIN_DISPLAYINFO_GPU_MEMORY_FILE=${WPE_DISPLAYINFO_GPU_MEMORY_FILE} \
    -DPLUGIN_DISPLAYINFO_GPU_MEMORY_FREE_PATTERN=${WPE_DISPLAYINFO_GPU_MEMORY_FREE_PATTERN} \
    -DPLUGIN_DISPLAYINFO_GPU_MEMORY_TOTAL_PATTERN=${WPE_DISPLAYINFO_GPU_MEMORY_TOTAL_PATTERN} \
    -DPLUGIN_DISPLAYINFO_GPU_MEMORY_UNIT_MULTIPLIER=${WPE_DISPLAYINFO_GPU_MEMORY_UNIT_MULTIPLIER} \
    ,-DPLUGIN_DISPLAYINFO=OFF, \
"

