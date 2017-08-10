package com.sharekeg.streetpal.Androidversionapi;

import com.sharekeg.streetpal.safeplace.DirectionModules.Route;

import java.util.List;

/**
 * Created by Lmis on 4/22/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
