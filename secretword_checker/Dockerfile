FROM node:18.12-slim as build-stage

WORKDIR /opt
COPY . /opt

RUN npm install && npm run build

FROM node:18.12-slim as production-stage

WORKDIR /opt
COPY . /opt

RUN apt update && apt upgrade -y && apt install tini -y
RUN npm install --production

COPY --from=build-stage /opt/dist ./dist

ENTRYPOINT ["/usr/bin/tini", "--"]

USER node

EXPOSE 3000

ENV NODE_ENV prod

CMD ["node", "dist/app.js"]

