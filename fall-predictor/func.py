import io
import json
import logging

from fdk import response


def handler(ctx, data: io.BytesIO = None):
    try:
        answer = ""
        json_obj = json.loads(data.getvalue())
        if isinstance(json_obj, list):
            answer = []
            for input in json_obj:
                answer.append(random())
        else:
            answer = "input object is not an array of objects:" + str(json_obj)
            raise Exception(answer)
    except (Exception, ValueError) as ex:
        logging.getLogger().info('error parsing json payload: ' + str(ex))

    logging.getLogger().info("Inside Python ML function")
    return response.Response(
        ctx, response_data=json.dumps(answer),
        headers={"Content-Type": "application/json"}
    )
