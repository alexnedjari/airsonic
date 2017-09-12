/*
 * This file is part of Minisonic.
 *
 *  Minisonic is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Minisonic is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Minisonic.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2015 (C) Sindre Mehus
 */

package org.minisonic.player.service.sonos;

import org.minisonic.player.domain.MediaFile;

import java.util.List;

/**
 * @author Sindre Mehus
 * @version $Id$
 */
class AlbumList {

    private final List<MediaFile> albums;
    private final int total;

    public AlbumList(List<MediaFile> albums, int total) {
        this.albums = albums;
        this.total = total;
    }

    public List<MediaFile> getAlbums() {
        return albums;
    }

    public int getTotal() {
        return total;
    }
}
