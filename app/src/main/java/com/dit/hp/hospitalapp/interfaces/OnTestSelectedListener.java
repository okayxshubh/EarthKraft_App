package com.dit.hp.hospitalapp.interfaces;

import com.dit.hp.hospitalapp.Modals.TestsPojo;

import java.util.List;

public interface OnTestSelectedListener {

    void onTestSelected(List<TestsPojo> selectedTests);
}
