package am.mandiri.util


class UnexpectedResponseFromServer : RuntimeException("Unexpected response.")

class PaginationDidEnd : RuntimeException("Pagination did end.")