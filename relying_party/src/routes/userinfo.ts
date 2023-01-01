import { Router, Request, Response, NextFunction } from "express";

import { isLoggedIn } from "../libs/utils";

export const router = Router();

router.get("/userinfo", isLoggedIn, (req: Request, res: Response, next: NextFunction) => {
  res.render("userinfo", { userinfo: JSON.stringify(req.user, null, 2)});
});
