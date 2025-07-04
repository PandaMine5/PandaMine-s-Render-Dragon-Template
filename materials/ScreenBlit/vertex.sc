$input  a_texcoord0, a_position 
$output v_texcoord0


#include <bgfx_shader.h>


void main() {
    vec2 pos = (a_position.xy * 2.0) - 1.0;
    v_texcoord0 = a_texcoord0;

    gl_Position = vec4(pos, 0.0, 1.0);
}