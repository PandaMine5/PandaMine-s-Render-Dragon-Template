$input a_color0, a_position, a_texcoord0, a_texcoord1
#ifdef INSTANCING
  $input i_data1, i_data2, i_data3
#endif
$output v_color0, v_fog, v_texcoord0, v_lightmapUV

#include <bgfx_shader.h>

uniform vec4 FogColor;
uniform vec4 RenderChunkFogAlpha;
uniform vec4 FogAndDistanceControl;
uniform vec4 ViewPositionAndTime;
uniform vec4 MeshContext;
uniform vec4 SubPixelOffset;




vec4 applyAAJitter(vec3 worldPos) {
    mat4 offsetProj = u_proj;

    #if BGFX_SHADER_LANGUAGE_GLSL
      offsetProj[2][0] += SubPixelOffset.x;
      offsetProj[2][1] -= SubPixelOffset.y;
    #else
      offsetProj[0][2] += SubPixelOffset.x;
      offsetProj[1][2] -= SubPixelOffset.y;
    #endif

    return mul(offsetProj, mul(u_view, vec4(worldPos, 1.0)));
}


float computeFog(float distance) {
  vec4 fogSettings = mix(
    FogAndDistanceControl,
    vec4(0.99, 1.0, 100000.0, 100000.0),
    bvec4_splat(MeshContext.x > 0.5)
  );

  float fogStart = fogSettings.x;
  float fogEnd = fogSettings.y;
  float renderDistance = fogSettings.z;

  float fogDistance = distance / renderDistance + RenderChunkFogAlpha.x;
  return clamp((fogDistance - fogStart) / (fogEnd - fogStart), 0.0, 1.0);
}



void main() {
  vec4 color;
  mat4 model = u_model[0];
  vec3 worldPos = mul(model, vec4(a_position, 1.0)).xyz;
  float distance = length(ViewPositionAndTime.xyz - worldPos);



  #ifdef INSTANCING
      model = mtxFromCols(i_data1, i_data2, i_data3, vec4(0, 0, 0, 1));
  #else
      model;
  #endif



  #ifdef RENDER_AS_BILLBOARDS
    worldPos += vec3(0.5, 0.5, 0.5);
    vec3 viewDir = normalize(worldPos - ViewPositionAndTime.xyz);

    vec3 boardPlane = normalize(vec3(viewDir.z, 0.0, -viewDir.x));
    
    worldPos -= cross(viewDir, boardPlane) * (a_color0.z - 0.5) + boardPlane * (a_color0.x - 0.5);
    color = vec4_splat(1.0);
  #else
    color = a_color0;
  #endif





  #ifdef TRANSPARENT
    if (a_color0.a < 0.95)
      color.a = mix(a_color0.a, 1.0, clamp(distance, 0.0, 1.0));
  #endif



  v_texcoord0 = a_texcoord0;
  v_lightmapUV = a_texcoord1;
  v_color0 = color;
  v_fog = vec4(FogColor.rgb, computeFog(distance));



  gl_Position = applyAAJitter(worldPos);
}