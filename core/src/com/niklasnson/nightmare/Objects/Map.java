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
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {

  private static final String forgroundLayerName  =  "Map";

  private TiledMap                   tiledMap;
  private OrthogonalTiledMapRenderer tiledMapRenderer;
  private OrthographicCamera         camera;
  private SpriteBatch                batch;

  private TiledMapTileLayer          foregroundLayer;


  public Map (int level, OrthographicCamera camera, SpriteBatch batch) {
    this.camera = camera;
    this.batch = batch;
    initialize(level, camera, batch);
  }

  private void initialize (int level, OrthographicCamera camera, SpriteBatch batch) {
    Gdx.app.log("[Map]", "initialize level " + level);
    tiledMap = new TmxMapLoader().load("map/level." + level + ".tmx");
    foregroundLayer = (TiledMapTileLayer) tiledMap.getLayers().get(forgroundLayerName);
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
  }

  public void updateCamera () {
    tiledMapRenderer.setView(camera);
  }

  public void drawForeground () {
    tiledMapRenderer.renderTileLayer(foregroundLayer);
  }

}
