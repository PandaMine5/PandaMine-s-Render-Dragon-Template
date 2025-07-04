$input v_color0


#include <bgfx_shader.h>


void main() {
    gl_FragColor = vec4(v_color0.rgb, v_color0.a);
}
