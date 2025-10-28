/**
 * Markdown/HTML 到 Javadoc 转换器
 */

/**
 * 将 Markdown 格式的描述转换为 Javadoc 格式
 */
export function convertToJavadoc(description: string | undefined, title?: string): string | undefined {
  if (!description && !title) {
    return undefined;
  }

  let result = '';

  // 添加标题
  if (title) {
    result += title + '\n';
  }

  // 添加描述
  if (description) {
    if (title) {
      result += '<p>\n';
    }
    result += convertMarkdownToJavadoc(description);
  }

  return result.trim();
}

/**
 * 转换 Markdown 到 Javadoc 格式
 */
function convertMarkdownToJavadoc(markdown: string): string {
  let result = markdown;

  // 转换粗体: **text** -> <b>text</b>
  result = result.replace(/\*\*([^*]+)\*\*/g, '<b>$1</b>');

  // 转换斜体: *text* 或 _text_ -> <i>text</i>
  result = result.replace(/\*([^*]+)\*/g, '<i>$1</i>');
  result = result.replace(/_([^_]+)_/g, '<i>$1</i>');

  // 转换行内代码: `code` -> {@code code}
  result = result.replace(/`([^`]+)`/g, '{@code $1}');

  // 转换链接: [text](url) -> <a href="url">text</a>
  result = result.replace(/\[([^\]]+)\]\(([^)]+)\)/g, '<a href="$2">$1</a>');

  // 转换换行: \n -> <p>
  result = result.replace(/\n\n+/g, '\n<p>\n');

  // 转义特殊字符
  result = escapeJavadocSpecialChars(result);

  return result;
}

/**
 * 转义 Javadoc 中的特殊字符
 */
function escapeJavadocSpecialChars(text: string): string {
  // 只转义 */ 注释结束符，不使用 HTML 实体
  text = text.replace(/\*\//g, '*\\/');

  // 转义 @ 符号（除非是 Javadoc 标签），使用反斜杠而非 HTML 实体
  text = text.replace(/@(?!code|link|see|param|return|throws|deprecated)/g, '\\@');

  return text;
}

/**
 * 生成完整的 Javadoc 注释块
 */
export function generateJavadoc(description: string | undefined, title?: string, indent: string = ''): string {
  const content = convertToJavadoc(description, title);

  if (!content) {
    return '';
  }

  const lines = content.split('\n');

  if (lines.length === 1) {
    // 单行注释
    return `${indent}/* ${lines[0]} */`;
  }

  // 多行注释
  return [
    `${indent}/*`,
    ...lines.map(line => `${indent} * ${line}`),
    `${indent} */`
  ].join('\n');
}
