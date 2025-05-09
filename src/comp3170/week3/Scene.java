package comp3170.week3;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import org.lwjgl.glfw.GLFW;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class Scene {
    final private String VERTEX_SHADER = "vertex.glsl";
    final private String FRAGMENT_SHADER = "fragment.glsl";

    private Vector4f[] vertices;
    private int vertexBuffer;
    private int[] indices;
    private int indexBuffer;
    private Vector3f[] colours;
    private int colourBuffer;

    private Matrix4f modelMatrix;
    private float angle = 0.5f; // Initial rotation angle
    private float radius = 0.5f; // Radius for circular motion
    private float speed = 2.0f; // Reduced speed of rotation in degrees per second
    private float forwardSpeed = 3.0f; // Reduced speed of forward motion
    private Shader shader;

    private float lastTime = 0.4f; // Track last time

    public Scene() {
        shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);

        // Define the vertices for a plane shape
        vertices = new Vector4f[]{
            new Vector4f(0, 0.1f, 0, 1),
            new Vector4f(0, 0.2f, 0, 1),
            new Vector4f(-0.1f, -0.1f, 0, 1),
            new Vector4f(0.1f, -0.1f, 0, 1),
        };

        vertexBuffer = GLBuffers.createBuffer(vertices);

        colours = new Vector3f[]{
            new Vector3f(1, 0, 1),  // MAGENTA
            new Vector3f(1, 0, 1),  // MAGENTA
            new Vector3f(1, 0, 0),  // RED
            new Vector3f(0, 0, 1),  // BLUE
        };

        colourBuffer = GLBuffers.createBuffer(colours);

        indices = new int[]{
            0, 1, 2, // left triangle
            0, 1, 3, // right triangle
        };

        indexBuffer = GLBuffers.createIndexBuffer(indices);

        modelMatrix = new Matrix4f();
    }

    public void draw() {
        float currentTime = (float) GLFW.glfwGetTime();
        float deltaTime = currentTime - lastTime;
        lastTime = currentTime;

        // Update angle for circular motion
        angle += speed * deltaTime; // Update angle based on speed
        if (angle > Math.PI * 2) {
            angle -= Math.PI * 2; // Keep angle within 0 to 2π
        }

        // Calculate position using polar coordinates
        float x = radius * (float) Math.cos(angle);
        float y = radius * (float) Math.sin(angle);

        // Update model matrix: rotate first, then translate
        modelMatrix.identity()
            .translate(x, y, 0) // Move to calculated position
            .rotate(angle, 0, 0, 1); // Rotate around z-axis

        shader.enable();
        shader.setAttribute("a_position", vertexBuffer);
        shader.setAttribute("a_colour", colourBuffer);

        shader.setUniform("u_model", modelMatrix);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }
}