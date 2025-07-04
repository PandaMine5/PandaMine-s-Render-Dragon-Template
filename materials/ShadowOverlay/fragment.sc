$input v_color0

#include <bgfx_shader.h>


uniform vec4 ShadowColor;


void main() {
    gl_FragColor = vec4(mix(v_color0.rgb, ShadowColor.rgb, v_color0.a), 1.0);
}