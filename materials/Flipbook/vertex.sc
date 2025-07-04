
$input  a_texcoord0, a_position
$output v_texcoord0, v_texcoord1


#include <bgfx_shader.h>


uniform vec4 VBlendControl;


void main() {
    v_texcoord0 = a_texcoord0;
    v_texcoord0.y += VBlendControl.x;
    v_texcoord1 = a_texcoord0;
    v_texcoord1.y += VBlendControl.y;

    gl_Position = mul(u_modelViewProj, vec4(a_position, 1.0));
}