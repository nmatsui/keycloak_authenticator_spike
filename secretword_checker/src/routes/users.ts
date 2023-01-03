import { Router, Request, Response, NextFunction } from "express";
import dotenv from "dotenv";

export const router = Router();
dotenv.config();

router.put("/:userid/check/", (req: Request, res: Response, next: NextFunction) => {
  if (!process.env?.SECRET_KEY || !process.env?.SECRET_WORD) {
    throw new Error("missing required environment variables");
  }

  if (!(process.env.SECRET_KEY in req.body) || req.body[process.env.SECRET_KEY] !== process.env.SECRET_WORD) {
    throw {
      status: 406,
      message: "secret word do not match"
    }
  }

  const msg = {
    message: "check ok"
  }
  console.log(JSON.stringify(msg));
  res.json(msg);
});
