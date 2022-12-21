import io
import json
import logging
import random

from fdk import response


def handler(ctx, data: io.BytesIO = None):
    try:
        json_obj = json.loads(data.getvalue())
        if isinstance(json_obj, dict) and 'listName' in json_obj:
            answer = []
            for input in json_obj:
                answer.append(random())
        else:
            raise Exception("input object is not an array of objects:" + str(json_obj))
    except (Exception, ValueError) as ex:
        logging.getLogger().info('error parsing json payload: ' + str(ex))

    logging.getLogger().info("Inside Python ML function")
    return response.Response(
        ctx, response_data=json.dumps(answer),
        headers={"Content-Type": "application/json"}
    )
