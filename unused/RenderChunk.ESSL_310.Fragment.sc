/*
* Available Macros:
*
* Passes:
* - ALPHA_TEST_PASS
* - DEPTH_ONLY_PASS
* - DEPTH_ONLY_OPAQUE_PASS
* - OPAQUE_PASS
* - TRANSPARENT_PASS
*
* Instancing:
* - INSTANCING__OFF (not used)
* - INSTANCING__ON (not used)
*
* RenderAsBillboards:
* - RENDER_AS_BILLBOARDS__OFF (not used)
* - RENDER_AS_BILLBOARDS__ON (not used)
*
* Seasons:
* - SEASONS__OFF
* - SEASONS__ON
*/

precision mediump float;
precision highp int;
uniform highp vec4 FogColor;
SAMPLER2D_HIGHP_AUTOREG(s_LightMapTexture);
#ifndef DEPTH_ONLY_OPAQUE_PASS
SAMPLER2D_HIGHP_AUTOREG(s_MatTexture);
#endif
#if defined(SEASONS__ON) && (defined(ALPHA_TEST_PASS) || defined(OPAQUE_PASS))
SAMPLER2D_HIGHP_AUTOREG(s_SeasonsTexture);
#endif
#if !defined(DEPTH_ONLY_OPAQUE_PASS) && !defined(DEPTH_ONLY_PASS)
in highp vec4 v_color0;
#endif
in highp vec4 v_fog;
in highp vec2 v_lightmapUV;
#ifndef DEPTH_ONLY_OPAQUE_PASS
centroid in highp vec2 v_texcoord0;
#endif
layout(location = 0) out highp vec4 gl_FragColor;
void main() {
#if defined(ALPHA_TEST_PASS) && defined(SEASONS__OFF)
    highp vec4 _504 = texture(s_MatTexture, v_texcoord0);
    if (_504.w < 0.5)
#endif
#if defined(ALPHA_TEST_PASS) && defined(SEASONS__ON)
    highp vec4 _802 = v_color0;
    highp vec4 _553 = texture(s_MatTexture, v_texcoord0);
    if (_553.w < 0.5)
#endif
#ifdef DEPTH_ONLY_PASS
    highp vec4 _488 = texture(s_MatTexture, v_texcoord0);
    if (_488.w < 0.5)
#endif
#if defined(ALPHA_TEST_PASS) || defined(DEPTH_ONLY_PASS)
    {
        discard;
    }
#endif
#if defined(ALPHA_TEST_PASS) && defined(SEASONS__OFF)
    highp vec4 _517 = _504;
    highp vec3 _519 = _517.xyz * v_color0.xyz;
    _504 = vec4(_519.x, _519.y, _519.z, _517.w);
    highp vec4 _556 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz * _519.xyz, _504.w);
    highp vec4 _690 = v_fog;
    highp vec3 _576 = mix(_556.xyz, FogColor.xyz, vec3(_690.w));
    gl_FragColor = vec4(_576.x, _576.y, _576.z, _556.w);
#endif
#if defined(ALPHA_TEST_PASS) && defined(SEASONS__ON)
    highp vec3 _555 = v_color0.xyz;
    highp vec3 _609 = (_553.xyz * mix(vec3(1.0), texture(s_SeasonsTexture, v_color0.xy).xyz * 2.0, vec3(_555.z))).xyz * vec3(_802.w);
    highp vec4 _557 = vec4(_609.x, _609.y, _609.z, _553.w);
    _557.w = 1.0;
    _553 = _557;
    highp vec4 _644 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz * _557.xyz, _553.w);
    highp vec4 _780 = v_fog;
    highp vec3 _664 = mix(_644.xyz, FogColor.xyz, vec3(_780.w));
    gl_FragColor = vec4(_664.x, _664.y, _664.z, _644.w);
#endif
#ifdef DEPTH_ONLY_PASS
    highp vec4 _524 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz, 1.0);
    highp vec4 _628 = v_fog;
    highp vec3 _544 = mix(_524.xyz, FogColor.xyz, vec3(_628.w));
    gl_FragColor = vec4(_544.x, _544.y, _544.z, _524.w);
#endif
#ifdef DEPTH_ONLY_OPAQUE_PASS
    highp vec4 _496 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz, 1.0);
    highp vec4 _591 = v_fog;
    highp vec3 _516 = mix(_496.xyz, FogColor.xyz, vec3(_591.w));
    gl_FragColor = vec4(_516.x, _516.y, _516.z, _496.w);
#endif
#if defined(OPAQUE_PASS) && defined(SEASONS__OFF)
    highp vec4 _699 = v_color0;
    highp vec4 _526 = texture(s_MatTexture, v_texcoord0);
    highp vec3 _508 = _526.xyz * v_color0.xyz;
    highp vec4 _499 = vec4(_508.x, _508.y, _508.z, _526.w);
    _499.w = _699.w;
    highp vec4 _548 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz * _499.xyz, _499.w);
    highp vec4 _677 = v_fog;
    highp vec3 _568 = mix(_548.xyz, FogColor.xyz, vec3(_677.w));
    gl_FragColor = vec4(_568.x, _568.y, _568.z, _548.w);
#endif
#if defined(OPAQUE_PASS) && defined(SEASONS__ON)
    highp vec4 _781 = v_color0;
    highp vec4 _571 = texture(s_MatTexture, v_texcoord0);
    highp vec3 _547 = v_color0.xyz;
    highp vec3 _595 = (_571.xyz * mix(vec3(1.0), texture(s_SeasonsTexture, v_color0.xy).xyz * 2.0, vec3(_547.z))).xyz * vec3(_781.w);
    highp vec4 _549 = vec4(_595.x, _595.y, _595.z, _571.w);
    _549.w = 1.0;
    highp vec4 _545 = _549;
    highp vec4 _630 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz * _549.xyz, _545.w);
    highp vec4 _759 = v_fog;
    highp vec3 _650 = mix(_630.xyz, FogColor.xyz, vec3(_759.w));
    gl_FragColor = vec4(_650.x, _650.y, _650.z, _630.w);
#endif
#ifdef TRANSPARENT_PASS
    highp vec4 _705 = v_color0;
    highp vec4 _502 = texture(s_MatTexture, v_texcoord0);
    _502.w *= _705.w;
    highp vec4 _515 = _502;
    highp vec3 _517 = _515.xyz * v_color0.xyz;
    _502 = vec4(_517.x, _517.y, _517.z, _515.w);
    highp vec4 _554 = vec4(texture(s_LightMapTexture, v_lightmapUV).xyz * _517.xyz, _502.w);
    highp vec4 _683 = v_fog;
    highp vec3 _574 = mix(_554.xyz, FogColor.xyz, vec3(_683.w));
    gl_FragColor = vec4(_574.x, _574.y, _574.z, _554.w);
#endif
}

