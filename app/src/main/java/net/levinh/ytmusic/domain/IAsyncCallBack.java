package net.levinh.ytmusic.domain;

import java.util.Objects;

/**
 * Created by virus on 12/10/2016.
 */

public interface IAsyncCallBack {
    void onFinish(int type, Object result);
}
