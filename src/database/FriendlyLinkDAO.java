package database;

import bean.FriendlyLink;
import util.StringUtil;

import static meta.Definitions.TableFriendlyLink.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张启 on 2016/4/18.
 * dao for {@link FriendlyLink}
 */
public class FriendlyLinkDAO {

    public static ArrayList<FriendlyLink> getFriendlyLinks(long ownerId) {
        ArrayList<FriendlyLink> friendlyLinks = new ArrayList<>();
        String sql = "select * from " + TABLE_FRIENDLY_LINK
                + " where " + COLUMN_OWNER_ID + "=" + ownerId;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                friendlyLinks.add(FriendlyLink.newInstance(rs));
            }
            return friendlyLinks;
        } catch (SQLException e) {
            e.printStackTrace();
            return friendlyLinks;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static boolean createFriendlyLink(FriendlyLink friendlyLink) {
        String sql = "insert into " + TABLE_FRIENDLY_LINK + "("
                + COLUMN_OWNER_ID + ","
                + COLUMN_LINK_URL + ") values ("
                + friendlyLink.getOwnerId() + ",'"
                + StringUtil.escape(friendlyLink.getLinkUrl()) + "')";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean deleteFriendlyLink(long id) {
        String sql = "delete from " + TABLE_FRIENDLY_LINK
                + " where " + COLUMN_ID + "=" + id;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

}
