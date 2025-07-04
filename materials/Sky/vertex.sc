$input  a_color0, a_position
$output v_color0


#include <bgfx_shader.h>


uniform vec4 SkyColor;
uniform vec4 FogColor;


void main() {
    v_color0 = mix(SkyColor, FogColor, a_color0.x);

    gl_Position = mul(u_viewProj, vec4(a_position, 1.0));
}