package org.schabi.newpipe.local.holder;

import android.view.View;
import android.view.ViewGroup;

import org.schabi.newpipe.R;
import org.schabi.newpipe.database.LocalItem;
import org.schabi.newpipe.database.playlist.PlaylistDuplicatesEntry;
import org.schabi.newpipe.database.playlist.PlaylistMetadataEntry;
import static org.schabi.newpipe.database.playlist.model.PlaylistEntity.LIKES_PLAYLIST_ID;
import org.schabi.newpipe.local.LocalItemBuilder;
import org.schabi.newpipe.local.history.HistoryRecordManager;
import org.schabi.newpipe.util.PicassoHelper;
import org.schabi.newpipe.util.Localization;

import java.time.format.DateTimeFormatter;

public class LocalPlaylistItemHolder extends PlaylistItemHolder {

    private static final float GRAYED_OUT_ALPHA = 0.6f;

    public LocalPlaylistItemHolder(final LocalItemBuilder infoItemBuilder, final ViewGroup parent) {
        super(infoItemBuilder, parent);
    }

    LocalPlaylistItemHolder(final LocalItemBuilder infoItemBuilder, final int layoutId,
                            final ViewGroup parent) {
        super(infoItemBuilder, layoutId, parent);
    }

    @Override
    public void updateFromItem(final LocalItem localItem,
                               final HistoryRecordManager historyRecordManager,
                               final DateTimeFormatter dateTimeFormatter) {
        if (!(localItem instanceof PlaylistMetadataEntry)) {
            return;
        }
        final PlaylistMetadataEntry item = (PlaylistMetadataEntry) localItem;

        itemTitleView.setText(item.name);
        itemStreamCountView.setText(Localization.localizeStreamCountMini(
                itemStreamCountView.getContext(), item.streamCount));
        if (item.uid == LIKES_PLAYLIST_ID) {
            itemUploaderView.setText(R.string.likes_playlist_name);
        } else {
            itemUploaderView.setVisibility(View.INVISIBLE);
        }

        if (item.uid == LIKES_PLAYLIST_ID) {
            PicassoHelper.loadLikesPlaylistThumbnail().into(itemThumbnailView);
        } else {
            PicassoHelper.loadPlaylistThumbnail(item.thumbnailUrl).into(itemThumbnailView);
        }

        if (item instanceof PlaylistDuplicatesEntry
                && ((PlaylistDuplicatesEntry) item).timesStreamIsContained > 0) {
            itemView.setAlpha(GRAYED_OUT_ALPHA);
        } else {
            itemView.setAlpha(1.0f);
        }

        super.updateFromItem(localItem, historyRecordManager, dateTimeFormatter);
    }
}
