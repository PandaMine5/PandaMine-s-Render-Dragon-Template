$input v_texcoord0, v_color0, v_fog, v_lightmapUV



#include <bgfx_shader.h>



SAMPLER2D_AUTOREG(s_MatTexture);
SAMPLER2D_AUTOREG(s_LightMapTexture);


void main() {
  #ifndef DEPTH_ONLY_OPAQUE
    vec4 albedo   = texture2D(s_MatTexture, v_texcoord0); // Block texture
    vec4 lightmap = texture2D(s_LightMapTexture, v_lightmapUV);


    #if defined(ALPHA_TEST)
      if (albedo.a < 0.5) {  discard;  }
    #endif



    albedo.rgb *= v_color0.rgb; // Block tint
    albedo.rgb *= lightmap.rgb; // Lightmap shading
    albedo.rgb = mix(albedo.rgb, v_fog.rgb, v_fog.a); // Fog effect 


    gl_FragColor = albedo;
  #else
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
  #endif
}