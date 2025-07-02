$input v_texcoord0, v_texcoord1


#include <bgfx_shader.sh>


SAMPLER2D_AUTOREG(s_BlitTexture);
uniform vec4 VBlendControl;

void main() {
    vec4 tex1 = texture2D(s_BlitTexture, v_texcoord0);
    vec4 tex2 = texture2D(s_BlitTexture, v_texcoord1);
    vec4 output;

    if (tex1.a < 0.01)
    {
        output = tex2;
    }

    else
    {
        vec4 output2;

        if (tex1.a >= 0.01)
        {
           output2 = mix(tex1, tex2, vec4_splat(VBlendControl.z));
        }

        else
        {
            output2 = tex1;
        }
        output = output2;
    }
    
    gl_FragColor = output;
}
