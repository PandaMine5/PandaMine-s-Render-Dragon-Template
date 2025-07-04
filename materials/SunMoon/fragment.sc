$input v_texcoord0

#include <bgfx_shader.h>


SAMPLER2D_AUTOREG(s_SunMoonTexture);
uniform vec4 SunMoonColor;


void main() {
    gl_FragColor = SunMoonColor * texture2D(s_SunMoonTexture, v_texcoord0);
}