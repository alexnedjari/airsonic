package org.minisonic.player.service;

import org.minisonic.player.dao.BookmarkDao;
import org.minisonic.player.domain.Bookmark;
import org.minisonic.player.domain.MediaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookmarkService {

    private final BookmarkDao dao;

    @Autowired
    public BookmarkService(BookmarkDao dao) {
        this.dao = dao;
    }

    public Bookmark getBookmarkForUserAndMediaFile(String username, MediaFile mediaFile) {
        return dao.getBookmarks(username)
                .stream()
                .filter(bookmark -> Objects.equals(mediaFile.getId(), bookmark.getMediaFileId()))
                .findFirst().orElse(null);
    }

    public void createOrUpdateBookmark(Bookmark bookmark) {
        dao.createOrUpdateBookmark(bookmark);
    }

    public void deleteBookmark(String username, int mediaFileId) {
        dao.deleteBookmark(username, mediaFileId);
    }

    public List<Bookmark> getBookmarks(String username) {
        return dao.getBookmarks(username);
    }
}