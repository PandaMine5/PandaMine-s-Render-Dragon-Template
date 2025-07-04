

precision highp float;

uniform mat4 u_model[1];
uniform mat4 u_view;
uniform mat4 u_proj;

uniform vec4 ViewPositionAndTime;
uniform vec4 MeshContext;
uniform vec4 FogColor;
uniform vec4 RenderChunkFogAlpha;
uniform vec4 FogAndDistanceControl;
uniform vec4 SubPixelOffset;

in vec3 a_position;
in vec2 a_texcoord0;
in vec2 a_texcoord1;
in vec4 a_color0;

#ifdef INSTANCING__ON
in vec4 i_data1, i_data2, i_data3;
#endif

out vec2 v_texcoord0;
out vec2 v_lightmapUV;
out vec4 v_color0;
out vec4 v_fog;
out vec3 v_worldPos;

void main() {
  mat4 model = u_model[0];

#ifdef INSTANCING__ON
  model[0] = vec4(i_data1.x, i_data2.x, i_data3.x, 0.0);
  model[1] = vec4(i_data1.y, i_data2.y, i_data3.y, 0.0);
  model[2] = vec4(i_data1.z, i_data2.z, i_data3.z, 0.0);
  model[3] = vec4(i_data1.w, i_data2.w, i_data3.w, 1.0);
#endif

  vec4 world = model * vec4(a_position, 1.0);
  v_worldPos = world.xyz;

  v_texcoord0 = a_texcoord0;
  v_lightmapUV = a_texcoord1;

  // Add subpixel jitter
  mat4 proj = u_proj;
  proj[2].x += SubPixelOffset.x;
  proj[2].y -= SubPixelOffset.y;

  // Calculate fog factor
  float dist = length(ViewPositionAndTime.xyz - v_worldPos);
  vec4 fogSettings = mix(FogAndDistanceControl, vec4(0.99, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
  float fogFactor = clamp(((dist / fogSettings.z + RenderChunkFogAlpha.x) - fogSettings.x) / (fogSettings.y - fogSettings.x), 0.0, 1.0);
  v_fog = vec4(FogColor.rgb, fogFactor);

  // Final color (alpha softened by distance if transparent)
  v_color0 = a_color0;

#ifdef TRANSPARENT_PASS
  float maxDist = fogSettings.w;
  v_color0.a = mix(a_color0.a, 1.0, clamp(dist / maxDist, 0.0, 1.0));
#endif

  // Output position
  gl_Position = proj * (u_view * vec4(v_worldPos, 1.0));
}






/*
* Available Macros:
*
* Passes:
* - ALPHA_TEST_PASS (not used)
* - DEPTH_ONLY_PASS (not used)
* - DEPTH_ONLY_OPAQUE_PASS (not used)
* - OPAQUE_PASS (not used)
* - TRANSPARENT_PASS
*
* Instancing:
* - INSTANCING__OFF
* - INSTANCING__ON
*
* RenderAsBillboards:
* - RENDER_AS_BILLBOARDS__OFF
* - RENDER_AS_BILLBOARDS__ON
*
* Seasons:
* - SEASONS__OFF (not used)
* - SEASONS__ON (not used)
*/

#ifdef TRANSPARENT_PASS
uniform vec4 MeshContext;
uniform vec4 FogAndDistanceControl;
#endif
uniform vec4 ViewPositionAndTime;
uniform vec4 RenderChunkFogAlpha;
uniform vec4 FogColor;
uniform vec4 SubPixelOffset;
#ifndef TRANSPARENT_PASS
uniform vec4 MeshContext;
uniform vec4 FogAndDistanceControl;
#endif
in vec4 a_color0;
in vec2 a_texcoord1;
in vec3 a_position;
in vec2 a_texcoord0;
#ifdef INSTANCING__ON
in vec4 i_data1;
in vec4 i_data2;
in vec4 i_data3;
#endif
centroid out vec2 v_texcoord0;
void main() {
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    vec4 _491 = u_model[0] * vec4(a_position, 1.0);
    vec3 _492 = _491.xyz;
    vec4 _512 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
    mat4 _576 = u_proj;
    _576[2].x += SubPixelOffset.x;
    _576[2].y -= SubPixelOffset.y;
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    vec4 _754 = i_data1;
    vec4 _755 = i_data2;
    vec4 _756 = i_data3;
    mat4 _537;
    _537[0] = vec4(_754.x, _755.x, _756.x, 0.0);
    _537[1] = vec4(_754.y, _755.y, _756.y, 0.0);
    _537[2] = vec4(_754.z, _755.z, _756.z, 0.0);
    _537[3] = vec4(_754.w, _755.w, _756.w, 1.0);
    vec4 _599 = _537 * vec4(a_position, 1.0);
    vec3 _583 = _599.xyz;
    vec4 _607 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
    mat4 _671 = u_proj;
    _671[2].x += SubPixelOffset.x;
    _671[2].y -= SubPixelOffset.y;
#endif
#if defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    v_color0 = a_color0;
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    v_fog = vec4(FogColor.xyz, clamp((((length(ViewPositionAndTime.xyz - _492) / _512.z) + RenderChunkFogAlpha.x) - _512.x) / (_512.y - _512.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    vec3 _554 = (u_model[0] * vec4(a_position, 1.0)).xyz;
    vec3 _627 = _554 + vec3(0.5);
    vec3 _634 = normalize(_627 - ViewPositionAndTime.xyz);
    vec3 _637 = normalize(cross(vec3(0.0, 1.0, 0.0), _634));
    vec3 _624 = a_color0.xyz;
    vec3 _657 = _627 - ((cross(_634, _637) * (_624.z - 0.5)) + (_637 * (_624.x - 0.5))); // Attention!
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    vec4 _526 = u_model[0] * vec4(a_position, 1.0);
    vec3 _527 = _526.xyz;
    vec4 _847 = a_color0;
    float _590 = length(ViewPositionAndTime.xyz - _527);
#endif
// Approximation, matches 28 cases out of 30
#if defined(INSTANCING__OFF) && (defined(RENDER_AS_BILLBOARDS__ON) || defined(TRANSPARENT_PASS))
    vec4 _576 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    mat4 _693 = u_proj;
    _693[2].x += SubPixelOffset.x;
    _693[2].y -= SubPixelOffset.y;
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    vec4 _911 = i_data1;
    vec4 _912 = i_data2;
    vec4 _913 = i_data3;
    mat4 _599;
    _599[0] = vec4(_911.x, _912.x, _913.x, 0.0);
    _599[1] = vec4(_911.y, _912.y, _913.y, 0.0);
    _599[2] = vec4(_911.z, _912.z, _913.z, 0.0);
    _599[3] = vec4(_911.w, _912.w, _913.w, 1.0);
    vec3 _645 = (_599 * vec4(a_position, 1.0)).xyz;
    vec3 _722 = _645 + vec3(0.5);
    vec3 _729 = normalize(_722 - ViewPositionAndTime.xyz);
    vec3 _732 = normalize(cross(vec3(0.0, 1.0, 0.0), _729));
    vec3 _719 = a_color0.xyz;
    vec3 _752 = _722 - ((cross(_729, _732) * (_719.z - 0.5)) + (_732 * (_719.x - 0.5))); // Attention!
    vec4 _671 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
    mat4 _788 = u_proj;
    _788[2].x += SubPixelOffset.x;
    _788[2].y -= SubPixelOffset.y;
#endif
#if defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    v_color0 = vec4(1.0);
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    v_fog = vec4(FogColor.xyz, clamp((((length(ViewPositionAndTime.xyz - _657) / _576.z) + RenderChunkFogAlpha.x) - _576.x) / (_576.y - _576.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    v_fog = vec4(FogColor.xyz, clamp((((length(ViewPositionAndTime.xyz - _583) / _607.z) + RenderChunkFogAlpha.x) - _607.x) / (_607.y - _607.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    v_fog = vec4(FogColor.xyz, clamp((((length(ViewPositionAndTime.xyz - _752) / _671.z) + RenderChunkFogAlpha.x) - _671.x) / (_671.y - _671.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    mat4 _640 = u_proj;
    _640[2].x += SubPixelOffset.x;
    _640[2].y -= SubPixelOffset.y;
    vec4 _736 = a_color0;
    if (_847.w < 0.949999988079071044921875)
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
    vec3 _589 = (u_model[0] * vec4(a_position, 1.0)).xyz;
    vec4 _1017 = a_color0;
    vec3 _691 = _589 + vec3(0.5);
    vec3 _698 = normalize(_691 - ViewPositionAndTime.xyz);
    vec3 _701 = normalize(cross(vec3(0.0, 1.0, 0.0), _698));
    vec3 _688 = a_color0.xyz;
    vec3 _721 = _691 - ((cross(_698, _701) * (_688.z - 0.5)) + (_701 * (_688.x - 0.5))); // Attention!
    float _660 = length(ViewPositionAndTime.xyz - _721);
    vec4 _640 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
    mat4 _757 = u_proj;
    _757[2].x += SubPixelOffset.x;
    _757[2].y -= SubPixelOffset.y;
    vec4 _893 = vec4(1.0);
    if (_1017.w < 0.949999988079071044921875)
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    vec4 _850 = i_data1;
    vec4 _851 = i_data2;
    vec4 _852 = i_data3;
    mat4 _571;
    _571[0] = vec4(_850.x, _851.x, _852.x, 0.0);
    _571[1] = vec4(_850.y, _851.y, _852.y, 0.0);
    _571[2] = vec4(_850.z, _851.z, _852.z, 0.0);
    _571[3] = vec4(_850.w, _851.w, _852.w, 1.0);
    vec4 _633 = _571 * vec4(a_position, 1.0);
    vec3 _617 = _633.xyz;
    vec4 _965 = a_color0;
    float _684 = length(ViewPositionAndTime.xyz - _617);
    vec4 _670 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
    mat4 _734 = u_proj;
    _734[2].x += SubPixelOffset.x;
    _734[2].y -= SubPixelOffset.y;
    vec4 _830 = a_color0;
    if (_965.w < 0.949999988079071044921875)
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
    vec4 _1007 = i_data1;
    vec4 _1008 = i_data2;
    vec4 _1009 = i_data3;
    mat4 _633;
    _633[0] = vec4(_1007.x, _1008.x, _1009.x, 0.0);
    _633[1] = vec4(_1007.y, _1008.y, _1009.y, 0.0);
    _633[2] = vec4(_1007.z, _1008.z, _1009.z, 0.0);
    _633[3] = vec4(_1007.w, _1008.w, _1009.w, 1.0);
    vec3 _679 = (_633 * vec4(a_position, 1.0)).xyz;
    vec4 _1144 = a_color0;
    vec3 _785 = _679 + vec3(0.5);
    vec3 _792 = normalize(_785 - ViewPositionAndTime.xyz);
    vec3 _795 = normalize(cross(vec3(0.0, 1.0, 0.0), _792));
    vec3 _782 = a_color0.xyz;
    vec3 _815 = _785 - ((cross(_792, _795) * (_782.z - 0.5)) + (_795 * (_782.x - 0.5))); // Attention!
    float _754 = length(ViewPositionAndTime.xyz - _815);
    vec4 _734 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
    mat4 _851 = u_proj;
    _851[2].x += SubPixelOffset.x;
    _851[2].y -= SubPixelOffset.y;
    vec4 _987 = vec4(1.0);
    if (_1144.w < 0.949999988079071044921875)
#endif
#ifdef TRANSPARENT_PASS
    {
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
        vec4 _550 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
        _736.w = mix(_847.w, 1.0, clamp(_590 / _550.w, 0.0, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
        vec4 _612 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
        _893.w = mix(_1017.w, 1.0, clamp(_660 / _612.w, 0.0, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
        vec4 _644 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
        _830.w = mix(_965.w, 1.0, clamp(_684 / _644.w, 0.0, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
        vec4 _706 = mix(FogAndDistanceControl, vec4(0.9900000095367431640625, 1.0, 100000.0, 100000.0), bvec4(MeshContext.x > 0.5));
        _987.w = mix(_1144.w, 1.0, clamp(_754 / _706.w, 0.0, 1.0));
#endif
#ifdef TRANSPARENT_PASS
    }
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    v_color0 = _736;
    v_fog = vec4(FogColor.xyz, clamp((((_590 / _576.z) + RenderChunkFogAlpha.x) - _576.x) / (_576.y - _576.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
    v_color0 = _893;
    v_fog = vec4(FogColor.xyz, clamp((((_660 / _640.z) + RenderChunkFogAlpha.x) - _640.x) / (_640.y - _640.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    v_color0 = _830;
    v_fog = vec4(FogColor.xyz, clamp((((_684 / _670.z) + RenderChunkFogAlpha.x) - _670.x) / (_670.y - _670.x), 0.0, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
    v_color0 = _987;
    v_fog = vec4(FogColor.xyz, clamp((((_754 / _734.z) + RenderChunkFogAlpha.x) - _734.x) / (_734.y - _734.x), 0.0, 1.0));
#endif
    v_lightmapUV = a_texcoord1;
    v_texcoord0 = a_texcoord0;
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    v_worldPos = _492;
    gl_Position = _576 * (u_view * vec4(_491.xyz, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    v_worldPos = _554;
    gl_Position = _693 * (u_view * vec4(_657, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && !defined(TRANSPARENT_PASS)
    v_worldPos = _583;
    gl_Position = _671 * (u_view * vec4(_599.xyz, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && !defined(TRANSPARENT_PASS)
    v_worldPos = _645;
    gl_Position = _788 * (u_view * vec4(_752, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    v_worldPos = _527;
    gl_Position = _640 * (u_view * vec4(_526.xyz, 1.0));
#endif
#if defined(INSTANCING__OFF) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
    v_worldPos = _589;
    gl_Position = _757 * (u_view * vec4(_721, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__OFF) && defined(TRANSPARENT_PASS)
    v_worldPos = _617;
    gl_Position = _734 * (u_view * vec4(_633.xyz, 1.0));
#endif
#if defined(INSTANCING__ON) && defined(RENDER_AS_BILLBOARDS__ON) && defined(TRANSPARENT_PASS)
    v_worldPos = _679;
    gl_Position = _851 * (u_view * vec4(_815, 1.0));
#endif
}

