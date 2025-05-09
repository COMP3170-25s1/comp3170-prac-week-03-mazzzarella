package comp3170.week3;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL41.*;

import java.io.File;
import java.io.IOException;

import comp3170.OpenGLException;
import comp3170.IWindowListener;
import comp3170.ShaderLibrary;
import comp3170.Window;

public class Week3 implements IWindowListener {
    private Window window;
    private Scene scene;

    final private File DIRECTORY = new File("src/comp3170/week3");
    private int width = 800;
    private int height = 800;

    public Week3() throws OpenGLException {
        window = new Window("Week 3 prac", width, height, this);
        window.setResizable(true);
        window.run();
    }

    @Override
    public void init() {
        new ShaderLibrary(DIRECTORY);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        scene = new Scene();
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT);
        scene.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, width, height);
    }

    @Override
    public void close() {
        // TODO: Implement close functionality
    }

    public static void main(String[] args) throws IOException, OpenGLException {
        new Week3();
    }
}