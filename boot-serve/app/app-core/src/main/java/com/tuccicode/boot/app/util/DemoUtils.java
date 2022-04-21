package com.tuccicode.boot.app.util;

import com.tuccicode.raccoon.exception.BizException;

/**
 * @author tucci.lee
 */
public class DemoUtils {

    private static final Long ADMIN_UID = 1L;
    private static final Long SYS_ROLE_ID = 112650695508754432L;
    private static final Long[] SYS_RES_IDS = {
            28174904473944064L,
            28175098825408512L,
            28516794918502401L,
            28516877999276032L,
            28516935251525632L,
            28517012728709120L,
            28517103883517953L,
            112063924660076544L,
            112064419969630208L,
            112064548923506688L,
            112065408583860224L,
            112304735788204032L,
            112304837034508288L,
            112304921109331968L,
            112305002013261824L,
            112766904753455104L,
            113296483343663104L,
            113296609537687552L,
            113296742077693952L,
            113296815033417728L,
            113296905349365760L,
            113297095770767360L,
            113297279363842048L,
            113297348314005504L,
            113297416005877760L,
            113297479004323840L,
            113297703277953024L,
            113297768126087168L,
            113297841698373632L,
            113297895578402816L,
            113326873903104000L,
            113327217995415552L,
            113328033569439744L,
            113329345681948672L,
            113329497700302848L,
            113329647516647424L,
            113329855000477696L,
            113329976727568384L};

    public static void isAdminUser(Long uid) {
        if (ADMIN_UID.equals(uid)) {
            throw new BizException(-2, "演示项目无法操作");
        }
    }

    public static void isSysRole(Long roleId) {
        if (SYS_ROLE_ID.equals(roleId)) {
            throw new BizException(-2, "演示项目无法操作");
        }
    }

    public static void isSysRes(Long resId) {
        for (Long id : SYS_RES_IDS) {
            if (id.equals(resId)) {
                throw new BizException(-2, "演示项目无法操作");
            }
        }
    }

    public static void notOperateTask() {
        throw new BizException(-2, "演示项目无法操");
    }

}