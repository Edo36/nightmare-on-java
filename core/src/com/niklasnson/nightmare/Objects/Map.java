/*
 * MIT License
 *
 * Copyright (c) [2017] [Niklas Nilsson]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.niklasnson.nightmare.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.niklasnson.nightmare.Assets;
import com.niklasnson.nightmare.Constants;

public class Map {

  private static final String foregroundLayerName = "Map";

  private TiledMap                   tiledMap;
  private OrthogonalTiledMapRenderer tiledMapRenderer;
  private OrthographicCamera         camera;
  private SpriteBatch                batch;

  private float                      mapTileSize;
  private TiledMapTileLayer          foregroundLayer;


  public Map (int level, OrthographicCamera camera, SpriteBatch batch) {
    Gdx.app.log("[Map]", "constructor");
    this.camera = camera;
    this.batch = batch;
    initialize(level, camera, batch);
  }

  /**
   * Initialize the map
   * @param level
   * @param camera
   * @param batch
   */
  private void initialize (int level, OrthographicCamera camera, SpriteBatch batch) {
    Gdx.app.log("[Map]", "initialize level " + level);
    tiledMap = new TmxMapLoader().load("map/level." + level + ".tmx");
    foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get(foregroundLayerName);

    mapTileSize = foregroundLayer.getTileWidth();

    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
  }

  /**
   * Initialize tiles on screen
   * @param world
   */
  public void initalizeTiles (World world) {
    for (int row = 0; row < foregroundLayer.getHeight(); row++) {
      for (int col = 0; col < foregroundLayer.getWidth(); col++) {
        TiledMapTileLayer.Cell cell = foregroundLayer.getCell(col, row);
        if (cell != null && cell.getTile() != null) {
          final int tileCol = col;
          final int tileRow = row;
          TiledMapTile tile = cell.getTile();

          Block block = new Block();
          block.createBody(world, col, row);
        }
      }
    }
  }

  /**
   * Update camera on screen
   */
  public void updateCamera () {
    tiledMapRenderer.setView(camera);
  }

  /**
   * Draw tiles on screen
   */
  public void drawForegroundLayer () {
    tiledMapRenderer.renderTileLayer(foregroundLayer);
  }

  /**
   * Draw the background image at fixed position on screen
   */
  public void drawBackgroundLayer () {
    batch.draw(
        Assets.background,
        (camera.position.x - (Constants.backgroundImageWidth/2)),
        0,
        Constants.width,
        Constants.height
    );
  }

}
