import psycopg2
from urllib import parse 
parse.uses_netloc.append("postgres")

URL_CONNECT = ""


class DbAccessor:

    def __init__(self, url):
        self.url = parse.urlparse(url)
        self.conn = psycopg2.connect(
            database=self.url.path[1:],
            user=self.url.username,
            password=self.url.password,
            host=self.url.hostname,
            port=self.url.port
        )
        self.cursor = None

    def connect(self):
        if self.cursor is not None:
            self.cursor.close()
            self.cursor = None

        self.cursor = self.conn.cursor()
        return self.cursor

    def rollback(self):
        self.conn.roleback()

    def close(self):
        self.conn.close()
        self.cursor.close()

    def commit(self):
        self.conn.commit()


def save_stamp(stamp_url):

    db_accessor = DbAccessor(URL_CONNECT)
    cursor = db_accessor.connect()
    try:
        cursor.execute('INSERT INTO test1 (data1) VALUES (%s)', (stamp_url,))
        db_accessor.commit()
    except Exception:
        db_accessor.rollback()
    finally:
        db_accessor.close()

if __name__ == '__main__':
    save_stamp("test_url")
