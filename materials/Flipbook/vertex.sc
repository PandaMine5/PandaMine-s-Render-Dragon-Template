$input  a_position, a_texcoord0
$output v_texcoord0, v_texcoord1


#include <bgfx_shader.sh>


uniform vec4 VBlendControl;


void main() {
    vec2 t01 = a_texcoord0;
    t01.y += VBlendControl.x;
    vec2 t02 = a_texcoord0;
    t02 += VBlendControl.y;


    v_texcoord0 = t01;
    v_texcoord1 = t02;

    gl_Position = mul(u_modelViewProj, vec4(a_position, 1.0));
}