package io.slavstudio.ray1;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Main {
    public static void main(String[] args) {
        // Initialization
        //--------------------------------------------------------------------------------------
        final int screenWidth = 800;
        final int screenHeight = 450;
        Raylib.Vector2 ballPosition = new Jaylib.Vector2();

        InitWindow(screenWidth, screenHeight, "raylib [core] example - 2d camera mouse zoom");

        Raylib.Camera2D camera = new Raylib.Camera2D();
        camera.zoom(2.0f);

        Texture texture = LoadTexture("resources/player.png"); // Failed
        Texture texture1 = LoadTexture("player.png"); // Failed

        SetTargetFPS(60);
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!WindowShouldClose()) {

            if (IsKeyDown(KEY_D)) {
                ballPosition.x(ballPosition.x()+2);
            }
            if (IsKeyDown(KEY_A)) {
                ballPosition.x(ballPosition.x()-2);
            }
            if (IsKeyDown(KEY_W)) {
                ballPosition.y(ballPosition.y()-2);
            }
            if (IsKeyDown(KEY_S)) {
                ballPosition.y(ballPosition.y()+2);
            }
            // Update
            //----------------------------------------------------------------------------------
            if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT))
            {
                Raylib.Vector2 delta = GetMouseDelta();
                delta = Vector2Scale(delta, -1.0f/camera.zoom());

                camera.target(Vector2Add(camera.target(), delta));
            }

            // Zoom based on mouse wheel
            float wheel = GetMouseWheelMove();
            if (wheel != 0) {
                Raylib.Vector2 mouseWorldPos = GetScreenToWorld2D(GetMousePosition(), camera);

                camera.offset(GetMousePosition());

                camera.target(mouseWorldPos);

                // Zoom increment
                final float zoomIncrement = 0.125f;

                camera.zoom(camera.zoom() + wheel*zoomIncrement);
                if (camera.zoom() < zoomIncrement) { camera.zoom(zoomIncrement); }
            }

            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            BeginDrawing();
            ClearBackground(BLACK);

            BeginMode2D(camera);

            rlPushMatrix();
            rlTranslatef(0, 25*50, 0);
            rlRotatef(90, 1, 0, 0);
            DrawGrid(100, 50);
            rlPopMatrix();

            DrawCircleV(ballPosition, 20, YELLOW);

            EndMode2D();

            DrawText("Mouse right button drag to move, mouse wheel to zoom", 10, 10, 20, WHITE);

            EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        CloseWindow();
        //--------------------------------------------------------------------------------------
    }
}
