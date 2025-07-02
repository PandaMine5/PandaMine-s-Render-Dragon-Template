$input v_color0

#include <bgfx_shader.sh>

uniform vec4 StarsColor;

void main() {
    gl_FragColor = vec4(v_color0.rgb * StarsColor.rgb * v_color0.a, v_color0.a);
}