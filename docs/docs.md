I'll write my shader notes here later, I'm PandaMine5
This my private folder for learning don't go here, respect privacy

Day 1:
Interpolations

All uv interolations

smooth - default
flat - will make blocks split colors for two triangles on face
noperspective - linear interpolation in screen space (no perspective correction). What ts has to mean?
centroid - Interpolates at a location guaranteed to be inside the primitive (helps avoid interpolation artifacts at edges, useful with multisampling).

How to use it: go in varying.def.sc

then we see
```
vec4 a_color0    : COLOR0;
vec2 a_texcoord1 : TEXCOORD1;
vec3 a_position  : POSITION;
vec2 a_texcoord0 : TEXCOORD0;




vec4 i_data1 : TEXCOORD7;
vec4 i_data2 : TEXCOORD6;
vec4 i_data3 : TEXCOORD5;



vec4          v_color0     : COLOR0;
vec4          v_fog        : COLOR2;
vec2          v_lightmapUV : TEXCOORD1;
-> centroid vec2 v_texcoord0  : TEXCOORD0;

vec3          v_worldPos   : TEXCOORD3;
```

let's change to: ```flat vec2 v_texcoord0  : TEXCOORD0;```