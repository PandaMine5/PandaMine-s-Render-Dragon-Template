$input v_texcoord0, v_color0, v_fog, v_lightmapUV



#include <bgfx_shader.h>



#ifndef DEPTH_ONLY_OPAQUE
  SAMPLER2D_AUTOREG(s_MatTexture);
  SAMPLER2D_AUTOREG(s_LightMapTexture);


  #if defined(SEASONS) && (defined(ALPHA_TEST) || defined(OPAQUE))
    SAMPLER2D_AUTOREG(s_SeasonsTexture);
  #endif
#endif



void main() {
  #ifndef DEPTH_ONLY_OPAQUE
    vec4 albedo   = texture2D(s_MatTexture, v_texcoord0); // Block texture
    vec4 lightmap = texture2D(s_LightMapTexture, v_lightmapUV);


    #if defined(ALPHA_TEST)
      if (albedo.a < 0.5) {  discard;  }
    #endif

    #ifndef TRANSPARENT
        albedo.a = 1.0;
    #endif


    #if defined(SEASONS) && (defined(ALPHA_TEST) || defined(OPAQUE))
      albedo.rgb *= mix(vec3_splat(1.0), 2.0 * texture2D(s_SeasonsTexture, v_color0.xy).rgb, v_color0.y);
      albedo.rgb *= v_color0.rrr;
    #else
      albedo.rgb *= v_color0;
    #endif



    albedo.rgb *= lightmap.rgb;
    albedo.rgb = mix(albedo.rgb, v_fog.rgb, v_fog.a); 

    gl_FragColor = albedo;
  #else
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
  #endif
}