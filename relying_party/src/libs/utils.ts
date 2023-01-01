import { Router, Request, Response, NextFunction } from "express";

export function isLoggedIn(req: Request, res: Response, next: NextFunction) {
  if (req.isAuthenticated()) {
    return next();
  } else {
    res.redirect("/login");
  }
}
