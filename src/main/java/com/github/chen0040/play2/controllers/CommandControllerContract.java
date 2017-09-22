package com.github.chen0040.play2.controllers;

import play.libs.F;
import play.mvc.Result;

import java.util.List;


/**
 * Created by xschen on 20/9/2016.
 */
public interface CommandControllerContract {
   F.Promise<Result> process(String info);
}
