#version 410

in vec4 a_position;  // Vertex position as a homogeneous vector in NDC 
in vec3 a_colour;    // Vertex colour RGB

out vec3 v_colour;   // To fragment shader
uniform mat4 u_model; // Model matrix

void main() {
    v_colour = a_colour;

    // Transform the vertex position by the model matrix
    gl_Position = u_model * a_position;
}