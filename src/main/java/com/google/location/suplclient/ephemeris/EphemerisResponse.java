// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.location.suplclient.ephemeris;

import com.google.location.suplclient.iono.GnssIonoModel;

import java.util.List;

/** A container for GNSS satellite ephemeris and Ionospheric model parameters. */
public class EphemerisResponse {

  /* A list of ephemeris for GNSS satellites */
  public final List<GnssEphemeris> ephList;

  /* The parameters of the ionospheric model */
  public final List<GnssIonoModel> ionoModelList;

  /* Constructor */
  public EphemerisResponse(List<GnssEphemeris> ephList, List<GnssIonoModel> ionoModelList) {
    this.ephList = ephList;
    this.ionoModelList = ionoModelList;
  }
}
