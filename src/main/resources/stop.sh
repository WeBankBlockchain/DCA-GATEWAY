#!/bin/bash

ps -ef|grep dca-gateway_ |grep -v grep| awk '{print $2}'|xargs kill -9
