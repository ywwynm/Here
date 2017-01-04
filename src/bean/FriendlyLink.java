package bean;

import static meta.Definitions.TableFriendlyLink.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 张启 on 2016/4/18.
 * friendly link bean
 */
public class FriendlyLink {

    private long id;
    private long ownerId;
    private String linkUrl;

    public FriendlyLink() {}

    public FriendlyLink(long id, long ownerId, String linkUrl) {
        this.id = id;
        this.ownerId = ownerId;
        this.linkUrl = linkUrl;
    }

    public static FriendlyLink newInstance(ResultSet rs) {
        try {
            long id = rs.getLong(COLUMN_ID);
            long ownerId = rs.getLong(COLUMN_OWNER_ID);
            String linkUrl = rs.getString(COLUMN_LINK_URL);
            return new FriendlyLink(id, ownerId, linkUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
