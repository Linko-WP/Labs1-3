package com.google.gwt.sample.stockwatcher.client;

/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;

/**
 * Allows table rows to dragged by their handle.
 */
public final class FlexTableRowDragController extends PickupDragController {

  private FlexTable draggableTable;

  private int dragRow;

  public FlexTableRowDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel, false);
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(false);
  }

  @Override
  public void dragEnd() {
    super.dragEnd();

    // cleanup
    draggableTable = null;
  }

  @Override
  public void setBehaviorDragProxy(boolean dragProxyEnabled) {
    if (!dragProxyEnabled) {
      // TODO implement drag proxy behavior
      throw new IllegalArgumentException();
    }
    super.setBehaviorDragProxy(dragProxyEnabled);
  }

  @Override
  protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel,
      boolean allowDroppingOnBoundaryPanel) {
    if (allowDroppingOnBoundaryPanel) {
      throw new IllegalArgumentException();
    }
    return super.newBoundaryDropController(boundaryPanel, allowDroppingOnBoundaryPanel);
  }

  FlexTable getDraggableTable() {
    return draggableTable;
  }

  int getDragRow() {
    return dragRow;
  }
  
}