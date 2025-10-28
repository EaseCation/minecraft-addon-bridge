/**
 * Handlebars 模板引擎封装
 */

import * as Handlebars from 'handlebars';
import * as fs from 'fs';
import * as path from 'path';

export class TemplateEngine {
  private handlebars: typeof Handlebars;
  private templateCache: Map<string, HandlebarsTemplateDelegate> = new Map();
  private templateDir: string;

  constructor(templateDir?: string) {
    this.handlebars = Handlebars.create();
    this.templateDir = templateDir || path.join(__dirname, '../templates');
    this.registerHelpers();
    this.registerPartials();
  }

  /**
   * 渲染模板
   * @param templateName - 模板文件名（如 "Record.hbs"）
   * @param data - 模板数据
   * @returns 渲染后的字符串
   */
  render(templateName: string, data: any): string {
    const template = this.getTemplate(templateName);
    return template(data);
  }

  /**
   * 获取模板（带缓存）
   */
  private getTemplate(templateName: string): HandlebarsTemplateDelegate {
    if (this.templateCache.has(templateName)) {
      return this.templateCache.get(templateName)!;
    }

    const templatePath = path.join(this.templateDir, templateName);

    if (!fs.existsSync(templatePath)) {
      throw new Error(`模板文件不存在: ${templatePath}`);
    }

    const templateSource = fs.readFileSync(templatePath, 'utf-8');
    const template = this.handlebars.compile(templateSource);

    this.templateCache.set(templateName, template);
    return template;
  }

  /**
   * 注册自定义 Handlebars helpers
   */
  private registerHelpers(): void {
    // Helper: 首字母大写
    this.handlebars.registerHelper('capitalize', (str: string) => {
      if (!str) return '';
      return str.charAt(0).toUpperCase() + str.slice(1);
    });

    // Helper: 首字母小写
    this.handlebars.registerHelper('uncapitalize', (str: string) => {
      if (!str) return '';
      return str.charAt(0).toLowerCase() + str.slice(1);
    });

    // Helper: 缩进
    this.handlebars.registerHelper('indent', (text: string, spaces: number) => {
      if (!text) return '';
      const indent = ' '.repeat(spaces);
      return text
        .split('\n')
        .map(line => (line.trim() ? indent + line : line))
        .join('\n');
    });

    // Helper: 连接数组
    this.handlebars.registerHelper('join', (arr: any[], separator: string) => {
      if (!arr) return '';
      return arr.join(separator);
    });

    // Helper: 格式化注解
    this.handlebars.registerHelper('formatAnnotation', (annotation: any) => {
      if (!annotation) return '';

      if (!annotation.parameters || Object.keys(annotation.parameters).length === 0) {
        return annotation.name;
      }

      const params = Object.entries(annotation.parameters)
        .map(([key, value]) => {
          if (key === 'value') {
            return String(value);
          }
          return `${key} = ${value}`;
        })
        .join(', ');

      return `${annotation.name}(${params})`;
    });

    // Helper: 条件判断（等于）
    this.handlebars.registerHelper('eq', (a: any, b: any) => {
      return a === b;
    });

    // Helper: 条件判断（不等于）
    this.handlebars.registerHelper('ne', (a: any, b: any) => {
      return a !== b;
    });
  }

  /**
   * 注册 partial 模板
   */
  private registerPartials(): void {
    const partialsDir = path.join(this.templateDir, 'partials');

    if (!fs.existsSync(partialsDir)) {
      return;
    }

    // 读取所有 partial 文件
    const partialFiles = fs.readdirSync(partialsDir).filter(file => file.endsWith('.hbs'));

    for (const file of partialFiles) {
      const partialName = file.replace('.hbs', '');
      const partialPath = path.join(partialsDir, file);
      const partialContent = fs.readFileSync(partialPath, 'utf-8');

      this.handlebars.registerPartial(partialName, partialContent);
    }
  }

  /**
   * 清除模板缓存
   */
  clearCache(): void {
    this.templateCache.clear();
  }
}
