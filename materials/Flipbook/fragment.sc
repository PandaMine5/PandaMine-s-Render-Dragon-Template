$input v_texcoord0, v_texcoord1


#include <bgfx_shader.h>


SAMPLER2D_AUTOREG(s_BlitTexture);
uniform vec4 VBlendControl;



void main() {
    vec4 currentFrame = texture2D(s_BlitTexture, v_texcoord0);
    vec4 nextFrame    = texture2D(s_BlitTexture, v_texcoord1);
    vec4 color = currentFrame;


    if (currentFrame.a < 0.01) {
        color = nextFrame;
    }

    else if (nextFrame.a >= 0.01) {
        color = mix(currentFrame, nextFrame, VBlendControl.z);
    }

    
    gl_FragColor = color;
}
